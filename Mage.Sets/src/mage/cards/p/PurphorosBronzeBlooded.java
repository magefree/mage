package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurphorosBronzeBlooded extends CardImpl {

    public PurphorosBronzeBlooded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to red is less than five, Purphuros isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.R, 5))
                .addHint(DevotionCount.R.getHint()));

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // {2}{R}: You may put a red creature card or an artifact creature card from your hand onto the battlefield. Sacrifice it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new PurphurosBronzeBloodedEffect(), new ManaCostsImpl<>("{2}{R}")));
    }

    private PurphorosBronzeBlooded(final PurphorosBronzeBlooded card) {
        super(card);
    }

    @Override
    public PurphorosBronzeBlooded copy() {
        return new PurphorosBronzeBlooded(this);
    }
}

class PurphurosBronzeBloodedEffect extends OneShotEffect {

    private static final String choiceText
            = "Put a red creature card or an artifact creature card from your hand onto the battlefield?";
    private static final FilterCard filter
            = new FilterCreatureCard("a red creature card or an artifact creature card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                new ColorPredicate(ObjectColor.RED)
        ));
    }

    PurphurosBronzeBloodedEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a red creature card or an artifact creature card " +
                "from your hand onto the battlefield. Sacrifice it at the beginning of the next end step";
    }

    private PurphurosBronzeBloodedEffect(final PurphurosBronzeBloodedEffect effect) {
        super(effect);
    }

    @Override
    public PurphurosBronzeBloodedEffect copy() {
        return new PurphurosBronzeBloodedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
            return true;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        if (!controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return true;
        }

        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect(
                        "sacrifice " + card.getName(),
                        source.getControllerId()
                ).setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
