package mage.cards.b;

import mage.MageInt;
import mage.MageItem;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BenthicAnomaly extends CardImpl {

    public BenthicAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, for each opponent, choose a creature that player controls. Create a token that's a copy of one of those creatures, except its power is equal to the total power of those creatures, its toughness is equal to the total toughness of those creatures, and it's a colorless Eldrazi creature.
        this.addAbility(new CastSourceTriggeredAbility(new BenthicAnomalyEffect()));
    }

    private BenthicAnomaly(final BenthicAnomaly card) {
        super(card);
    }

    @Override
    public BenthicAnomaly copy() {
        return new BenthicAnomaly(this);
    }
}

class BenthicAnomalyEffect extends OneShotEffect {

    BenthicAnomalyEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, choose a creature that player controls. " +
                "Create a token that's a copy of one of those creatures, " +
                "except its power is equal to the total power of those creatures, " +
                "its toughness is equal to the total toughness of those creatures, " +
                "and it's a colorless Eldrazi creature";
        this.setTargetPointer(new EachTargetPointer());
    }

    private BenthicAnomalyEffect(final BenthicAnomalyEffect effect) {
        super(effect);
    }

    @Override
    public BenthicAnomalyEffect copy() {
        return new BenthicAnomalyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getName());
            filter.add(new ControllerIdPredicate(opponentId));
            if (!game.getBattlefield().contains(filter, source, game, 1)) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanents.add(permanent);
            }
        }
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                permanent = RandomUtil.randomFromCollection(permanents);
                break;
            default:
                FilterPermanent filter = new FilterPermanent("a creature to create a copy of");
                filter.add(Predicates.or(
                        permanents
                                .stream()
                                .map(MageItem::getId)
                                .map(PermanentIdPredicate::new)
                                .collect(Collectors.toSet())
                ));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                player.choose(outcome, target, source, game);
                permanent = game.getPermanent(target.getFirstTarget());
        }
        int power = permanents
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        int toughness = permanents
                .stream()
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, false,
                false, null, power, toughness, false
        );
        return effect
                .setSavedPermanent(permanent)
                .setOnlyColor(ObjectColor.COLORLESS)
                .setOnlySubType(SubType.ELDRAZI)
                .apply(game, source);
    }
}
