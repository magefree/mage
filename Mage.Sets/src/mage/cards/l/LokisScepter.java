package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class LokisScepter extends CardImpl {

    public LokisScepter(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addAbility(new AnyColorManaAbility());

        // When Loki’s Scepter enters, gain control of target creature until end of turn. Untap that creature.
        // Until end of turn, it becomes a Villain in addition to its other types and gains haste.
        Ability ability = new EntersBattlefieldAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), false
        );
        ability.addEffect(new UntapTargetEffect().setText("Untap it"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste"));
        ability.addEffect(new AddCardSubTypeTargetEffect(
                SubType.VILLAIN, Duration.EndOfTurn
        ).setText("it becomes a Villain in addition to its other types"));
    }
    private LokisScepter(final LokisScepter card) {
        super(card);
    }
    @Override
    public LokisScepter copy() {
        return new LokisScepter(this);
    }
}
