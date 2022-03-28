
package mage.cards.d;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author LevelX2
 */
public final class DaringThief extends CardImpl {

    public DaringThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Inspired - Whenever Daring Thief becomes untapped, you may exchange control of target nonland permanent you control and target permanent an opponent controls that shares a card type with it.
        Ability ability = new InspiredAbility(new ExchangeControlTargetEffect(Duration.EndOfGame,
                "you may exchange control of target nonland permanent you control and target permanent an opponent controls that shares a card type with it", false, true), true);
        ability.addTarget(new TargetControlledPermanentSharingOpponentPermanentCardType());
        ability.addTarget(new DaringThiefSecondTarget());
        this.addAbility(ability);
    }

    private DaringThief(final DaringThief card) {
        super(card);
    }

    @Override
    public DaringThief copy() {
        return new DaringThief(this);
    }
}

class TargetControlledPermanentSharingOpponentPermanentCardType extends TargetControlledPermanent {

    public TargetControlledPermanentSharingOpponentPermanentCardType() {
        super();
        this.filter = this.filter.copy();
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        setTargetName("nonland permanent you control");
    }

    public TargetControlledPermanentSharingOpponentPermanentCardType(final TargetControlledPermanentSharingOpponentPermanentCardType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Set<CardType> cardTypes = getOpponentPermanentCardTypes(controllerId, game);
            Permanent permanent = game.getPermanent(id);
            for (CardType type : permanent.getCardType(game)) {
                if (cardTypes.contains(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // get all cardtypes from opponents permanents 
        Set<CardType> cardTypes = getOpponentPermanentCardTypes(sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    for (CardType type : permanent.getCardType(game)) {
                        if (cardTypes.contains(type)) {
                            possibleTargets.add(permanent.getId());
                            break;
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetControlledPermanentSharingOpponentPermanentCardType copy() {
        return new TargetControlledPermanentSharingOpponentPermanentCardType(this);
    }

    private EnumSet<CardType> getOpponentPermanentCardTypes(UUID sourceControllerId, Game game) {
        Player controller = game.getPlayer(sourceControllerId);
        EnumSet<CardType> cardTypes = EnumSet.noneOf(CardType.class);
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
                if (controller.hasOpponent(permanent.getControllerId(), game)) {
                    cardTypes.addAll(permanent.getCardType(game));
                }
            }
        }
        return cardTypes;
    }
}


class DaringThiefSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;

    public DaringThiefSecondTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        setTargetName("permanent an opponent controls that shares a card type with it");
    }

    public DaringThiefSecondTarget(final DaringThiefSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }


    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, source, game)) {
            Permanent target1 = game.getPermanent(source.getFirstTarget());
            Permanent opponentPermanent = game.getPermanent(id);
            if (target1 != null && opponentPermanent != null) {
                return target1.shareTypes(opponentPermanent, game);
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            MageObject targetSource = game.getObject(source);
            if (targetSource != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                    if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        if (permanent.shareTypes(firstTarget, game)) {
                            possibleTargets.add(permanent.getId());
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.Damage, playerId, source, game);
    }

    @Override
    public DaringThiefSecondTarget copy() {
        return new DaringThiefSecondTarget(this);
    }
}
