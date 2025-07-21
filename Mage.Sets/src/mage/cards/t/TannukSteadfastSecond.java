package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TannukSteadfastSecond extends CardImpl {

    public TannukSteadfastSecond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Artifact cards and red creature cards in your hand have warp {2}{R}.
        this.addAbility(new SimpleStaticAbility(new TannukSteadfastSecondEffect()));
    }

    private TannukSteadfastSecond(final TannukSteadfastSecond card) {
        super(card);
    }

    @Override
    public TannukSteadfastSecond copy() {
        return new TannukSteadfastSecond(this);
    }
}

class TannukSteadfastSecondEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                Predicates.and(
                        new ColorPredicate(ObjectColor.RED),
                        CardType.CREATURE.getPredicate()
                )
        ));
    }

    TannukSteadfastSecondEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "artifact cards and red creature cards in your hand have warp {2}{R}";
    }

    private TannukSteadfastSecondEffect(final TannukSteadfastSecondEffect effect) {
        super(effect);
    }

    @Override
    public TannukSteadfastSecondEffect copy() {
        return new TannukSteadfastSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(filter, game)) {
            game.getState().addOtherAbility(card, new WarpAbility(card, "{2}{R}"));
        }
        return true;
    }
}
