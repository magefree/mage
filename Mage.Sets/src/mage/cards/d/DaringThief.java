
package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        Ability ability = new InspiredAbility(new ExchangeControlTargetEffect(
                Duration.EndOfGame,
                "you may exchange control of target nonland permanent you control and target permanent an opponent controls that shares a card type with it",
                false,
                true
        ), true);
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
        withTargetName("nonland permanent you control");
    }

    private TargetControlledPermanentSharingOpponentPermanentCardType(final TargetControlledPermanentSharingOpponentPermanentCardType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Set<CardType> cardTypes = getOpponentPermanentCardTypes(playerId, game);

        if (super.canTarget(playerId, id, source, game)) {
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
        Set<CardType> cardTypes = getOpponentPermanentCardTypes(sourceControllerId, game);

        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                for (CardType type : permanent.getCardType(game)) {
                    if (cardTypes.contains(type)) {
                        possibleTargets.add(permanent.getId());
                        break;
                    }
                }
            }
        }
        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
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

    public DaringThiefSecondTarget() {
        super(StaticFilters.FILTER_OPPONENTS_PERMANENT);
        withTargetName("permanent an opponent controls that shares a card type with it");
    }

    private DaringThiefSecondTarget(final DaringThiefSecondTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        Permanent possiblePermanent = game.getPermanent(id);
        if (ownPermanent == null || possiblePermanent == null) {
            return false;
        }
        return super.canTarget(id, source, game) && ownPermanent.shareTypes(possiblePermanent, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (ownPermanent == null) {
                // playable or first target not yet selected
                // use all
                possibleTargets.add(permanent.getId());
            } else {
                // real
                // filter by shared type
                if (permanent.shareTypes(ownPermanent, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        possibleTargets.removeIf(id -> ownPermanent != null && ownPermanent.getId().equals(id));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        // AI hint with better outcome
        return super.chooseTarget(Outcome.GainControl, playerId, source, game);
    }

    @Override
    public DaringThiefSecondTarget copy() {
        return new DaringThiefSecondTarget(this);
    }
}
