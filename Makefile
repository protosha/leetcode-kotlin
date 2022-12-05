all: build

build: validate_gradlew
	@./gradlew build

clean: validate_gradlew
	@./gradlew clean

validate_gradlew: ./gradlew

new: validate_skeleton
	@read -p "Subproject name: " name; \
	echo "copying skeleton project into '$${name}' folder..."; \
	rsync -avq "./skeleton/" "./$${name}/" --exclude ".idea" --exclude "build"; \
	echo "renaming gradle file to '$${name}.gradle.kts'"; \
	mv "./$${name}/skeleton.gradle.kts" "./$${name}/$${name}.gradle.kts"; \
	echo "renaming subproject namespace root folder to '$$(echo $${name} | sed 's/-//g')'"; \
	mv "./$${name}/src/main/kotlin/com/github/protosha/skeleton" "./$${name}/src/main/kotlin/com/github/protosha/$$(echo $${name} | sed 's/-//g')"; \
	echo "renaming main class to '$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g')' and subproject namespace root to '$$(echo $${name} | sed 's/-//g')'"; \
	mv "./$${name}/src/main/kotlin/com/github/protosha/$$(echo $${name} | sed 's/-//g')/Skeleton.kt" "./$${name}/src/main/kotlin/com/github/protosha/$$(echo $${name} | sed 's/-//g')/$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g').kt"; \
	sed -i '' -E -e 's/object Skeleton/object '$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g')'/g' "./$${name}/src/main/kotlin/com/github/protosha/$$(echo $${name} | sed 's/-//g')/$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g').kt"; \
	sed -i '' -E -e 's/package com\.github\.protosha\.skeleton/package com\.github\.protosha\.'$$(echo $${name} | sed 's/-//g')'/g' "./$${name}/src/main/kotlin/com/github/protosha/$$(echo $${name} | sed 's/-//g')/$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g').kt"; \
	echo "changing main class in '$${name}.gradle.kts' to 'com.github.protosha.$$(echo $${name} | sed 's/-//g').$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g')'"; \
	sed -i '' -E -e 's/mainClass\.set\("com\.github\.protosha\.skeleton\.Skeleton"\)/mainClass\.set\("com\.github\.protosha\.'$$(echo $${name} | sed 's/-//g')'\.'$$(echo $${name} | perl -pe 's/(^|-)(\w)/\U$$2/g')'"\)/g' "./$${name}/$${name}.gradle.kts"; \


validate_skeleton: validate_gradlew
	@./gradlew :skeleton:build

