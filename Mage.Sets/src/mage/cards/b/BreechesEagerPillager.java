package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BreechesEagerPillager extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.PIRATE, "Pirate you control");

    public BreechesEagerPillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a Pirate you control attacks, choose one that hasn't been chosen this turn --
        // * Create a Treasure token.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, filter
        );
        ability.setModeTag("treasure");
        ability.getModes().setLimitUsageByOnce(true);

        // * Target creature can't block this turn.
        Mode mode = new Mode(new CantBlockTargetEffect(Duration.EndOfTurn))
                .setModeTag("target can't block");
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        // * Exile the top card of your library. You may play it this turn.
        ability.addMode(new Mode(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                        .setText("exile the top card of your library. You may play it this turn")
        ).setModeTag("exile top card"));

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private BreechesEagerPillager(final BreechesEagerPillager card) {
        super(card);
    }

    @Override
    public BreechesEagerPillager copy() {
        return new BreechesEagerPillager(this);
    }
}
