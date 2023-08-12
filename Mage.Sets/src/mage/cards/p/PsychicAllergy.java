package mage.cards.p;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class PsychicAllergy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public PsychicAllergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // As Psychic Allergy enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Damage)));

        // At the beginning of each opponent's upkeep, Psychic Allergy deals X damage to that player, where X is the number of nontoken permanents of the chosen color they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PsychicAllergyEffect(), TargetController.OPPONENT, false));

        // At the beginning of your upkeep, destroy Psychic Allergy unless you sacrifice two Islands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new DoUnlessControllerPaysEffect(new DestroySourceEffect(),
                        new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, false))).setText("destroy {this} unless you sacrifice two Islands"),
                TargetController.YOU,
                false));
    }

    private PsychicAllergy(final PsychicAllergy card) {
        super(card);
    }

    @Override
    public PsychicAllergy copy() {
        return new PsychicAllergy(this);
    }
}

class PsychicAllergyEffect extends OneShotEffect {

    public PsychicAllergyEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that player, where X is the number of nontoken permanents of the chosen color they control";
    }

    public PsychicAllergyEffect(PsychicAllergyEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(source.getSourceId() + "_color")));
            filter.add(TokenPredicate.FALSE);
            int damage = game.getBattlefield().countAll(filter, player.getId(), game);
            player.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public PsychicAllergyEffect copy() {
        return new PsychicAllergyEffect(this);
    }
}
