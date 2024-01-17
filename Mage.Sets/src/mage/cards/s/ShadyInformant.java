package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadyInformant extends CardImpl {

    public ShadyInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Shady Informant dies, it deals 2 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Disguise {2}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{2}")));
    }

    private ShadyInformant(final ShadyInformant card) {
        super(card);
    }

    @Override
    public ShadyInformant copy() {
        return new ShadyInformant(this);
    }
}
