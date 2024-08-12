package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static mage.abilities.dynamicvalue.common.StaticValue.*;
import static mage.constants.Duration.WhileOnBattlefield;

/**
 *
 * @author DreamWaker
 */
public final class ZinniaValleysVoice extends CardImpl {

    static final FilterCreaturePermanent bfilter = new FilterCreaturePermanent("other creatures with base power 1");

    static {
        bfilter.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
        bfilter.add(TargetController.YOU.getControllerPredicate());
        bfilter.add(AnotherPredicate.instance);

    }

    static final PermanentsOnBattlefieldCount bcount = new PermanentsOnBattlefieldCount(bfilter);;

    static final FilterNonlandCard cfilter = new FilterNonlandCard("creature spells");

    static {
        cfilter.add(CardType.CREATURE.getPredicate());
        cfilter.add(AnotherPredicate.instance);
    }

    public ZinniaValleysVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

	    // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creature spells you cast have offspring 2.
        this.addAbility(
                new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new OffspringAbility("{2}"), cfilter)
                        .setText("Creature spells you cast have offspring {2}.")));
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new OffspringAbility("{2}"), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_NON_TOKEN, true))
        //        new SimpleStaticAbility(new ZinniaValleysVoiceGainOffspringEffect())
        );

	    // Zinnia Valley's Voice gets +X/+0, where X is the number of other creatures you control with base power 1.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(bcount, get(0), WhileOnBattlefield)));

    }

    private ZinniaValleysVoice(final ZinniaValleysVoice card) {
        super(card);
    }

    @Override
    public ZinniaValleysVoice copy() {
        return new ZinniaValleysVoice(this);
    }
}

class ZinniaValleysVoiceGainOffspringEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard cfilter = new FilterCreatureCard("creature spell you cast");
    static{
        cfilter.add(AnotherPredicate.instance);
    }

    ZinniaValleysVoiceGainOffspringEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Creature spells you cast have offspring {2}. " +
                "<i>(You may pay an additional {2} as you cast a creature spell. " +
                "If you do, when that creature enters, create a 1/1 token copy of it.)</i>";
    }

    private ZinniaValleysVoiceGainOffspringEffect(final ZinniaValleysVoiceGainOffspringEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToGainOffspring = new HashSet<>();
        cardsToGainOffspring.addAll(controller.getHand().getCards(cfilter, game));
        cardsToGainOffspring.addAll(controller.getGraveyard().getCards(cfilter, game));
        controller.getLibrary().getCards(game).stream()
                .filter(c -> cfilter.match(c, game))
                .forEach(cardsToGainOffspring::add);
        game.getExile().getAllCardsByRange(game, controller.getId()).stream()
                .filter(c -> cfilter.match(c, game))
                .forEach(cardsToGainOffspring::add);
        game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY)
                .stream()
                .filter(card -> cfilter.match(card, game))
                .forEach(cardsToGainOffspring::add);
        game.getStack().stream()
                .filter(Spell.class::isInstance)
                .filter(s -> s.isControlledBy(controller.getId()))
                .filter(s -> cfilter.match((Spell) s, game))
                .map(s -> game.getCard(s.getSourceId()))
                .filter(Objects::nonNull)
                .forEach(cardsToGainOffspring::add);
        for (Card card : cardsToGainOffspring) {
            Ability ability = new OffspringAbility("{2}");
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getControllerOrOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public ZinniaValleysVoiceGainOffspringEffect copy() {
        return new ZinniaValleysVoiceGainOffspringEffect(this);
    }
}