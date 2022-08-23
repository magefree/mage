package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author freaisdead
 */
public final class ShanidSleepersScourge extends CardImpl {
    private static final FilterCreaturePermanent otherLegendaryCreaturesFilter = new FilterCreaturePermanent("other legendary creatures");
    private static final FilterSpell legendarySpellFilter = new FilterSpell("a legendary spell");
    private static final FilterPermanent legendaryLandFilter = new FilterPermanent("a legendary land");

    static {
        otherLegendaryCreaturesFilter.add(SuperType.LEGENDARY.getPredicate());

        legendarySpellFilter.add(SuperType.LEGENDARY.getPredicate());

        legendaryLandFilter.add(CardType.LAND.getPredicate());
        legendaryLandFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShanidSleepersScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other legendary creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                otherLegendaryCreaturesFilter,
                true)));
        // Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD,
                new DrawAndLoseEffect(1, 1),
                false,
                "Whenever you play a legendary land or cast a legendary spell, ",
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, null, legendaryLandFilter, true),
                new SpellCastControllerTriggeredAbility(null, legendarySpellFilter, false)
        ));
    }

    private ShanidSleepersScourge(final ShanidSleepersScourge card) {
        super(card);
    }

    @Override
    public ShanidSleepersScourge copy() {
        return new ShanidSleepersScourge(this);
    }
}


class DrawAndLoseEffect extends OneShotEffect {

    DrawAndLoseEffect(int drawAmount, int loseLifeAMount) {
        super(Outcome.Benefit);
        String cardRule = "a card";
        if (drawAmount > 1) {
            cardRule = String.format("%d cards", drawAmount);
        }
        this.staticText = String.format("draw %s and you lose %d life", cardRule, loseLifeAMount);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Effect drawEffectController = new DrawCardSourceControllerEffect(1);
        drawEffectController.apply(game, source);
        Effect drawEffectOpponent = new LoseLifeSourceControllerEffect(1);
        drawEffectOpponent.apply(game, source);

        return true;
    }

    private DrawAndLoseEffect(final DrawAndLoseEffect effect) {
        super(effect);
    }

    @Override
    public DrawAndLoseEffect copy() {
        return new DrawAndLoseEffect(this);
    }
}