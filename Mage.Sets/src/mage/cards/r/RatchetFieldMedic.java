package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatchetFieldMedic extends TransformingDoubleFacedCard {

    private static final FilterPermanent filterNontokenArtifacts = new FilterControlledArtifactPermanent();

    static {
        filterNontokenArtifacts.add(TokenPredicate.FALSE);
    }

    public RatchetFieldMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{2}{W}",
                "Ratchet, Rescue Racer",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "W"
        );

        // Ratchet, Field Medic
        this.getLeftHalfCard().setPT(2, 4);

        // More Than Meets the Eye {1}{W}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{W}"));

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, you may convert Ratchet. When you do, return target artifact card with mana value less than or equal to the amount of life you gained this turn from your graveyard to the battlefield tapped.
        Ability ability = new GainLifeControllerTriggeredAbility(
                new RatchetFieldMedicEffect(), true
        );
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Ratchet, Rescue Racer
        this.getRightHalfCard().setPT(1, 4);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever one or more nontoken artifacts you control are put into a graveyard from the battlefield, convert Ratchet. This ability triggers only once each turn.
        this.getRightHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}"), false, filterNontokenArtifacts
        ).setTriggerPhrase("Whenever one or more nontoken artifacts you control are put into a graveyard from the battlefield, ")
                .setTriggersLimitEachTurn(1));
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