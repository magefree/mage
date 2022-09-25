
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorcerySpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DjinnIlluminatus extends CardImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    public DjinnIlluminatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U/R}{U/R}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // <i>({U/R} can be paid with either {U} or {R}.)</i>
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EachSpellYouCastHasReplicateEffect(filter)));
    }

    private DjinnIlluminatus(final DjinnIlluminatus card) {
        super(card);
    }

    @Override
    public DjinnIlluminatus copy() {
        return new DjinnIlluminatus(this);
    }
}