package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class OmnispellAdept extends CardImpl {

    public OmnispellAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{U}, {T}: You may cast an instant or sorcery card from your hand without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new OmnispellAdeptEffect(), new ManaCostsImpl("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public OmnispellAdept(final OmnispellAdept card) {
        super(card);
    }

    @Override
    public OmnispellAdept copy() {
        return new OmnispellAdept(this);
    }
}

class OmnispellAdeptEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from your hand");

    public OmnispellAdeptEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery card from your hand without paying its mana cost";
    }

    public OmnispellAdeptEffect(final OmnispellAdeptEffect effect) {
        super(effect);
    }

    @Override
    public OmnispellAdeptEffect copy() {
        return new OmnispellAdeptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetCardInHand(filter);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                && controller.chooseUse(outcome, "Cast an instant or sorcery card from your hand without paying its mana cost?", source, game)) {
            Card cardToCast = null;
            boolean cancel = false;
            while (controller.canRespond() && !cancel) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    cardToCast = game.getCard(target.getFirstTarget());
                    if (cardToCast != null && cardToCast.getSpellAbility().canChooseTarget(game)) {
                        cancel = true;
                    }
                } else {
                    cancel = true;
                }
            }
            if (cardToCast != null) {
                controller.cast(cardToCast.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
        }
        return true;
    }
}
