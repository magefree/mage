package mage.game.command.emblems;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;

import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;


public final class TeyoAegisAdeptEmblem extends Emblem {

    // You get an emblem with “At the beginning of your end step, return target white creature card from your graveyard to the battlefield. You gain life equal to its toughness.”
    public TeyoAegisAdeptEmblem() {
        super("Emblem Teyo");

        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND,
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                TargetController.YOU, null, false);
        ability.addEffect(new GainLifeEqualToToughnessEffect());
        FilterCard filter = new FilterCreatureCard("white creature card from your graveyard");
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getAbilities().add(ability);
    }

    private TeyoAegisAdeptEmblem(final TeyoAegisAdeptEmblem card) {
        super(card);
    }

    @Override
    public TeyoAegisAdeptEmblem copy() {
        return new TeyoAegisAdeptEmblem(this);
    }

}

class GainLifeEqualToToughnessEffect extends OneShotEffect {

    GainLifeEqualToToughnessEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to its toughness";
    }

    private GainLifeEqualToToughnessEffect(final GainLifeEqualToToughnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
            }
        }
        return false;
    }

    @Override
    public GainLifeEqualToToughnessEffect copy() {
        return new GainLifeEqualToToughnessEffect(this);
    }
}
