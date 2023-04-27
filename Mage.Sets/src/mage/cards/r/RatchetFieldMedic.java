package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatchetFieldMedic extends CardImpl {

    public RatchetFieldMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.r.RatchetRescueRacer.class;

        // More Than Meets the Eye {1}{W}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{W}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, you may convert Ratchet. When you do, return target artifact card with mana value less than or equal to the amount of life you gained this turn from your graveyard to the battlefield tapped.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new RatchetFieldMedicEffect(), true
        ), new PlayerGainedLifeWatcher());
    }

    private RatchetFieldMedic(final RatchetFieldMedic card) {
        super(card);
    }

    @Override
    public RatchetFieldMedic copy() {
        return new RatchetFieldMedic(this);
    }
}

class RatchetFieldMedicEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("artifact card with mana value " +
            "less than or equal to the amount of life you gained this turn from your graveyard");

    static {
        filter.add(RatchetFieldMedicPredicate.instance);
    }

    RatchetFieldMedicEffect() {
        super(Outcome.Benefit);
        staticText = "convert {this}. When you do, return target artifact card with mana value less than or equal to the amount of life you gained this turn from your graveyard to the battlefield tapped";
    }

    private RatchetFieldMedicEffect(final RatchetFieldMedicEffect effect) {
        super(effect);
    }

    @Override
    public RatchetFieldMedicEffect copy() {
        return new RatchetFieldMedicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        // check not to transform twice the same side
        if (permanent == null || !permanent.transform(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

enum RatchetFieldMedicPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input
                .getManaValue()
                <= game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(input.getOwnerId());
    }
}