package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderingSparkmage extends CardImpl {

    public ThunderingSparkmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Thundering Sparkmage enters the battlefield, it deals X damage to target creature or planeswalker, where X is the number of creatures in your party.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(PartyCount.instance)
                .setText("it deals X damage to target creature or planeswalker, " +
                        "where X is the number of creatures in your party. " + PartyCount.getReminder()));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private ThunderingSparkmage(final ThunderingSparkmage card) {
        super(card);
    }

    @Override
    public ThunderingSparkmage copy() {
        return new ThunderingSparkmage(this);
    }
}
