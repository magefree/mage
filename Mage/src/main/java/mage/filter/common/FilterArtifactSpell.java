
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterSpell;

/**
 *
 * @author Jgod
 */
public class FilterArtifactSpell extends FilterSpell {

    public FilterArtifactSpell() {
        this("artifact spell");
    }

    public FilterArtifactSpell(String name) {
        super(name);
        this.add(CardType.ARTIFACT.getPredicate());
    }
}
