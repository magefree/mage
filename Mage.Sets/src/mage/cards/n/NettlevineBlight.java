package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class NettlevineBlight extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or land permanent");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public NettlevineBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or land
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent has "At the beginning of your end step, sacrifice this permanent and attach Nettlevine Blight to a creature or land you control."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new NettlevineBlightEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));

    }

    private NettlevineBlight(final NettlevineBlight card) {
        super(card);
    }

    @Override
    public NettlevineBlight copy() {
        return new NettlevineBlight(this);
    }
}

class NettlevineBlightEffect extends OneShotEffect {

    public NettlevineBlightEffect() {
        super(Outcome.Detriment);
        this.staticText = "sacrifice this permanent and attach {this} to a creature or land you control";
    }

    public NettlevineBlightEffect(final NettlevineBlightEffect effect) {
        super(effect);
    }

    @Override
    public NettlevineBlightEffect copy() {
        return new NettlevineBlightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent nettlevineBlight = game.getPermanent(source.getSourceId());
        Player newController = null;
        if (controller != null
                && nettlevineBlight != null) {
            Permanent enchantedPermanent = game.getPermanent(nettlevineBlight.getAttachedTo());
            if (enchantedPermanent != null) {
                newController = game.getPlayer(enchantedPermanent.getControllerId());
                enchantedPermanent.sacrifice(source, game);
            }
            if (newController != null) {
                FilterPermanent filter = new FilterPermanent("creature or land permanent you control");
                filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                        CardType.LAND.getPredicate()));
                filter.add(new ControllerIdPredicate(newController.getId()));
                filter.add(new CanBeEnchantedByPredicate(nettlevineBlight));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(newController.getId(), source, game)
                        && newController.choose(outcome, target, source, game)) {
                    Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
                    if (chosenPermanent != null) {
                        Card nettlevineBlightCard = game.getCard(source.getSourceId());
                        if (nettlevineBlightCard != null) {
                            game.getState().setValue("attachTo:" + nettlevineBlight.getId(), chosenPermanent);
                            chosenPermanent.addAttachment(nettlevineBlight.getId(), source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
