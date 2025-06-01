package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachmentAttachedToCardTypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrownOfTheAges extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a creature");

    static {
        filter.add(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE));
        filter.add(SubType.AURA.getPredicate());
    }

    public CrownOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {4}, {tap}: Attach target Aura attached to a creature to another creature.
        Ability ability = new SimpleActivatedAbility(new CrownOfTheAgesEffect(), new GenericManaCost(4));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CrownOfTheAges(final CrownOfTheAges card) {
        super(card);
    }

    @Override
    public CrownOfTheAges copy() {
        return new CrownOfTheAges(this);
    }
}

class CrownOfTheAgesEffect extends OneShotEffect {

    CrownOfTheAgesEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "attach target Aura attached to a creature to another creature";
    }

    private CrownOfTheAgesEffect(final CrownOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfTheAgesEffect copy() {
        return new CrownOfTheAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (aura == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("another creature");
        filter.add(Predicates.not(new PermanentIdPredicate(aura.getAttachedTo())));
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetPermanent(filter);
        target.withNotTarget(true);
        controller.choose(Outcome.Neutral, target, source, game);
        return Optional
                .ofNullable(target)
                .map(Target::getFirstTarget)
                .map(game::getPermanent)
                .map(permanent -> permanent.addAttachment(aura.getId(), source, game))
                .orElse(false);
    }
}
