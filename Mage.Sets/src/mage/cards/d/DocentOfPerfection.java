
package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.HumanWizardToken;
import mage.players.Player;

/**
 * @author fireshoes
 */
public final class DocentOfPerfection extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("an instant or sorcery spell");

    static {
        filterSpell.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public DocentOfPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.f.FinalIteration.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        // Then if you control three or more Wizards, transform Docent of Perfection.
        this.addAbility(new TransformAbility());
        Effect effect = new DocentOfPerfectionEffect();
        Ability ability = new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new HumanWizardToken()), filterSpell, false);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DocentOfPerfection(final DocentOfPerfection card) {
        super(card);
    }

    @Override
    public DocentOfPerfection copy() {
        return new DocentOfPerfection(this);
    }
}

class DocentOfPerfectionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Wizards");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DocentOfPerfectionEffect() {
        super(Outcome.Benefit);
        staticText = "Then if you control three or more Wizards, transform {this}";
    }

    public DocentOfPerfectionEffect(final DocentOfPerfectionEffect effect) {
        super(effect);
    }

    @Override
    public DocentOfPerfectionEffect copy() {
        return new DocentOfPerfectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) >= 3) {
                return new TransformSourceEffect().apply(game, source);
            }
        }
        return false;
    }
}