package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VATS extends CardImpl {

    public VATS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Choose any number of target creatures with equal toughness. Destroy the chosen creatures.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setText("choose any number of target creatures with equal toughness. Destroy the chosen creatures"));
        this.getSpellAbility().addTarget(new VATSTarget());
    }

    private VATS(final VATS card) {
        super(card);
    }

    @Override
    public VATS copy() {
        return new VATS(this);
    }
}

class VATSTarget extends TargetPermanent {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with equal toughness");

    VATSTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private VATSTarget(final VATSTarget target) {
        super(target);
    }

    @Override
    public VATSTarget copy() {
        return new VATSTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Permanent creature = game.getPermanent(id);
        return creature != null
                && this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .findFirst()
                .orElse(0) == creature.getToughness().getValue();
    }
}
