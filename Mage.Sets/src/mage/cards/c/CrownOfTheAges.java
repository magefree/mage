package mage.cards.c;

import mage.MageObject;
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
import mage.constants.Zone;
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

import java.util.UUID;

/**
 * @author spjspj
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrownOfTheAgesEffect(), new GenericManaCost(4));
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

    public CrownOfTheAgesEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura attached to a creature to another creature";
    }

    public CrownOfTheAgesEffect(final CrownOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfTheAgesEffect copy() {
        return new CrownOfTheAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getFirstTarget());
        if (aura == null) {
            return false;
        }
        Permanent fromPermanent = game.getPermanent(aura.getAttachedTo());
        Player controller = game.getPlayer(source.getControllerId());
        if (fromPermanent == null || controller == null) {
            return false;
        }
        boolean passed = true;
        FilterCreaturePermanent filterChoice = new FilterCreaturePermanent("another creature");
        filterChoice.add(Predicates.not(new PermanentIdPredicate(fromPermanent.getId())));

        Target chosenCreatureToAttachAura = new TargetPermanent(filterChoice);
        chosenCreatureToAttachAura.setNotTarget(true);

        if (chosenCreatureToAttachAura.canChoose(source.getControllerId(), source, game)
                && controller.choose(Outcome.Neutral, chosenCreatureToAttachAura, source, game)) {
            Permanent creatureToAttachAura = game.getPermanent(chosenCreatureToAttachAura.getFirstTarget());
            if (creatureToAttachAura != null) {
                if (passed) {
                    // Check the target filter
                    Target target = aura.getSpellAbility().getTargets().get(0);
                    if (target instanceof TargetPermanent) {
                        if (!target.getFilter().match(creatureToAttachAura, game)) {
                            passed = false;
                        }
                    }
                    // Check for protection
                    MageObject auraObject = game.getObject(aura.getId());
                    if (auraObject != null && creatureToAttachAura.cantBeAttachedBy(auraObject, source, game, true)) {
                        passed = false;
                    }
                }
                if (passed) {
                    fromPermanent.removeAttachment(aura.getId(), source, game);
                    creatureToAttachAura.addAttachment(aura.getId(), source, game);
                    return true;
                }
            }
        }
        return true;
    }
}
