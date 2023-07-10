package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author bobby-mccann
 */
public final class BillFernyBreeSwindler extends CardImpl {
    private static final FilterControlledPermanent horseYouControl = new FilterControlledPermanent("Horse you control");
    static {
        horseYouControl.add(SubType.HORSE.getPredicate());
    }

    public BillFernyBreeSwindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Bill Ferny, Bree Swindler becomes blocked, choose one --
        Ability ability = new BecomesBlockedAllTriggeredAbility(
                // * Create a Treasure token.
                new CreateTokenEffect(new TreasureToken()), false
        );
        ability.addMode(
                // * Target opponent gains control of target Horse you control. If they do, remove Bill Ferny from combat and create three Treasure tokens.
                new Mode(new GainControlTargetEffect(Duration.Custom, true))
                        .addTarget(new TargetOpponent())
                        .addTarget(new TargetControlledPermanent(horseYouControl))
                        .addEffect(new RemoveFromCombatSourceEffect())
                        .addEffect(new CreateTokenEffect(new TreasureToken(), 3))
        );
        this.addAbility(ability);
    }

    private BillFernyBreeSwindler(final BillFernyBreeSwindler card) {
        super(card);
    }

    @Override
    public BillFernyBreeSwindler copy() {
        return new BillFernyBreeSwindler(this);
    }
}
