package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class RakdosFirewheeler extends CardImpl {

    public RakdosFirewheeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Rakdos Firewheeler enters the battlefield, it deals 2 damage to target opponent and 2 damage to up to one target creature or planeswalker.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("it deals 2 damage to target opponent and 2 damage to up to one target creature or planeswalker");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1, new FilterCreatureOrPlaneswalkerPermanent(), false));
        this.addAbility(ability);
    }

    private RakdosFirewheeler(final RakdosFirewheeler card) {
        super(card);
    }

    @Override
    public RakdosFirewheeler copy() {
        return new RakdosFirewheeler(this);
    }
}
