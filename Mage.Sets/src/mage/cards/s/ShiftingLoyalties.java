
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShiftingLoyalties extends CardImpl {

    public ShiftingLoyalties(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");

        // Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>"));
        this.getSpellAbility().addTarget(new TargetPermanentsThatShareCardType());

    }

    public ShiftingLoyalties(final ShiftingLoyalties card) {
        super(card);
    }

    @Override
    public ShiftingLoyalties copy() {
        return new ShiftingLoyalties(this);
    }
}

class TargetPermanentsThatShareCardType extends TargetPermanent {

    public TargetPermanentsThatShareCardType() {
        super(2, 2, new FilterPermanent(), false);
        targetName = "permanents that share a card type";
    }

    public TargetPermanentsThatShareCardType(final TargetPermanentsThatShareCardType target) {
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
                return targetOne.shareTypes(targetTwo);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<CardType> cardTypes = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                for (CardType cardType :permanent.getCardType()) {
                    if (cardTypes.contains(cardType)) {
                        return true;
                    }
                }
                cardTypes.addAll(permanent.getCardType());
            }
        }
        return false;
    }

    @Override
    public TargetPermanentsThatShareCardType copy() {
        return new TargetPermanentsThatShareCardType(this);
    }
}
