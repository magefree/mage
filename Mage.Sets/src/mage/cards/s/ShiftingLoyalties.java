
package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShiftingLoyalties extends CardImpl {

    public ShiftingLoyalties(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>"));
        this.getSpellAbility().addTarget(new TargetPermanentsThatShareCardType());

    }

    private ShiftingLoyalties(final ShiftingLoyalties card) {
        super(card);
    }

    @Override
    public ShiftingLoyalties copy() {
        return new ShiftingLoyalties(this);
    }
}

class TargetPermanentsThatShareCardType extends TargetPermanent {

    TargetPermanentsThatShareCardType() {
        super(2, 2, StaticFilters.FILTER_PERMANENT, false);
        targetName = "permanents that share a card type";
    }

    private TargetPermanentsThatShareCardType(final TargetPermanentsThatShareCardType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            if (!getTargets().isEmpty()) {
                Permanent targetOne = game.getPermanent(getTargets().get(0));
                Permanent targetTwo = game.getPermanent(id);
                if (targetOne == null || targetTwo == null) {
                    return false;
                }
                return targetOne.shareTypes(targetTwo, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Set<CardType> cardTypes = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    for (CardType cardType : permanent.getCardType(game)) {
                        if (cardTypes.contains(cardType)) {
                            return true;
                        }
                    }
                    cardTypes.addAll(permanent.getCardType(game));
                }
            }
        }
        return false;
    }

    @Override
    public TargetPermanentsThatShareCardType copy() {
        return new TargetPermanentsThatShareCardType(this);
    }
}
