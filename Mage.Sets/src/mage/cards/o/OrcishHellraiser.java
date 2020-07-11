package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrcishHellraiser extends CardImpl {

    public OrcishHellraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Echo {R}
        this.addAbility(new EchoAbility("{R}"));

        // When Orcish Hellraiser dies, it deals 2 damage to target player or planeswalker.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private OrcishHellraiser(final OrcishHellraiser card) {
        super(card);
    }

    @Override
    public OrcishHellraiser copy() {
        return new OrcishHellraiser(this);
    }
}
