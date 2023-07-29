package mage.cards.n;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.FinalChapterAbilityResolvesTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NarciFableSinger extends CardImpl {

    public NarciFableSinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you sacrifice an enchantment, draw a card.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            StaticFilters.FILTER_PERMANENT_ENCHANTMENT
        ));

        // Whenever the final chapter ability of a Saga you control resolves, each opponent loses X life and you gain X life, where X is that Saga's mana value.
        this.addAbility(new FinalChapterAbilityResolvesTriggeredAbility(
            new NarciFableSingerEffect(), true
        ));
    }

    private NarciFableSinger(final NarciFableSinger card) {
        super(card);
    }

    @Override
    public NarciFableSinger copy() {
        return new NarciFableSinger(this);
    }
}

class NarciFableSingerEffect extends OneShotEffect {

    NarciFableSingerEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses X life and you gain X life, where X is that Saga's mana value.";
    }

    private NarciFableSingerEffect(final NarciFableSingerEffect ability) {
        super(ability);
    }

    @Override
    public NarciFableSingerEffect copy() {
        return new NarciFableSingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FixedTarget fixedTarget = targetPointer.getFixedTarget(game, source);
        if (fixedTarget == null) {
            return false;
        }

        MageObject saga = game.getObject(fixedTarget.getTarget());
        if (saga == null) {
            return false;
        }

        int value = saga.getManaValue();
        if (value > 0) {
            new LoseLifeOpponentsEffect(value).apply(game, source);
            new GainLifeEffect(value).apply(game, source);
        }
        return true;
    }
}