package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Notshauna, PurpleCrowbar
 */
public final class LokisScepter extends CardImpl {

    public LokisScepter(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Loki's Scepter enters, gain control of target creature until end of turn. Untap that creature. Until end of turn, it becomes a Villain in addition to its other types and gains haste.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), false
        );
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new AddCardSubTypeTargetEffect(
                SubType.VILLAIN, Duration.EndOfTurn
        ).setText("Until end of turn, it becomes a Villain in addition to its other types"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private LokisScepter(final LokisScepter card) {
        super(card);
    }

    @Override
    public LokisScepter copy() {
        return new LokisScepter(this);
    }
}
