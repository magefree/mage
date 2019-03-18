
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author jeffwadsworth
 */
public final class Evershrike extends CardImpl {

    public Evershrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/B}{W/B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Evershrike gets +2/+2 for each Aura attached to it.
        AuraAttachedCount amount = new AuraAttachedCount(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield)));

        // {X}{WB}{WB}: Return Evershrike from your graveyard to the battlefield. You may put an Aura card with converted mana cost X or less from your hand onto the battlefield attached to it. If you don't, exile Evershrike.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new EvershrikeEffect(), new ManaCostsImpl("{X}{W/B}{W/B}")));

    }

    public Evershrike(final Evershrike card) {
        super(card);
    }

    @Override
    public Evershrike copy() {
        return new Evershrike(this);
    }
}

class EvershrikeEffect extends OneShotEffect {

    public EvershrikeEffect() {
        super(Outcome.Benefit);
        staticText = "Return {this} from your graveyard to the battlefield. You may put an Aura card with converted mana cost X or less from your hand onto the battlefield attached to it. If you don't, exile {this}";
    }

    public EvershrikeEffect(final EvershrikeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card evershrikeCard = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && evershrikeCard != null) {
            if (evershrikeCard.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false)) {
                int xAmount = source.getManaCostsToPay().getX() + 1;
                Permanent evershrikePermanent = game.getPermanent(source.getSourceId());
                if (evershrikePermanent == null) {
                    return false;
                }
                boolean exileSource = true;
                FilterCard filterAuraCard = new FilterCard("Aura card with converted mana cost X or less from your hand");
                filterAuraCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
                filterAuraCard.add(new SubtypePredicate(SubType.AURA));
                filterAuraCard.add(new AuraCardCanAttachToPermanentId(evershrikePermanent.getId()));
                filterAuraCard.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xAmount));
                int count = controller.getHand().count(filterAuraCard, game);
                if (count > 0 && controller.chooseUse(Outcome.Benefit, "Do you wish to put an Aura card from your hand onto " + evershrikeCard.getIdName() + "?", source, game)) {
                    TargetCard targetAura = new TargetCard(Zone.HAND, filterAuraCard);
                    if (controller.choose(Outcome.Benefit, controller.getHand(), targetAura, game)) {
                        Card aura = game.getCard(targetAura.getFirstTarget());
                        if (aura != null) {
                            game.getState().setValue("attachTo:" + aura.getId(), evershrikePermanent);
                            if (controller.moveCards(aura, Zone.BATTLEFIELD, source, game)) {
                                evershrikePermanent.addAttachment(aura.getId(), game);
                            }
                            exileSource = false;
                        }
                    }
                }
                if (exileSource) {
                    controller.moveCards(evershrikeCard, Zone.EXILED, source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public EvershrikeEffect copy() {
        return new EvershrikeEffect(this);
    }
}
