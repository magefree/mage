package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TitanOfLittjara extends CardImpl {

    public TitanOfLittjara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As Titan of Littjara enters the battlefield, choose a creature type.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit));

        // Titan of Littjara is the chosen type in addition to its other types.
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddChosenSubtypeEffect()));

        // Whenever Titan of Littjara enters the battlefield or attacks, you may draw a card for each other creature you control that shares a creature type with it. If you do, discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new TitanOfLittjaraEffect(),
            true
        ));
    }

    private TitanOfLittjara(final TitanOfLittjara card) {
        super(card);
    }

    @Override
    public TitanOfLittjara copy() {
        return new TitanOfLittjara(this);
    }
}


class TitanOfLittjaraEffect extends OneShotEffect {

    TitanOfLittjaraEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card for each other creature you control that shares a creature type with it. If you do, discard a card.";
    }

    private TitanOfLittjaraEffect(final TitanOfLittjaraEffect effect) {
        super(effect);
    }

    @Override
    public TitanOfLittjaraEffect copy() {
        return new TitanOfLittjaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent titan = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (titan == null || controller == null) {
            return false;
        }

        FilterPermanent filter = new FilterControlledCreaturePermanent("other creature you control that shares a creature type with it");
        filter.add(new SharesCreatureTypePredicate(titan));
        filter.add(AnotherPredicate.instance);

        int count = game.getBattlefield().getActivePermanents(filter, controller.getId(), source, game).size();

        controller.drawCards(count, source, game);
        controller.discardOne(false, false, source, game);

        return true;
    }
}