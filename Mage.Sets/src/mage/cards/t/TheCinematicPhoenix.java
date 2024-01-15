package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheCinematicPhoenix extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("legendary creature card from your graveyard");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheCinematicPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // {1}, {T}: Return target legendary creature card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Tap six untapped creatures you control: Return The Cinematic Phoenix from your graveyard to the battlefield. If you tapped six legendary creatures this way, you win the game.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new TapTargetCost(new TargetControlledPermanent(6, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES))
        );
        ability.addEffect(new TheCinematicPhoenixEffect());
        this.addAbility(ability);
    }

    private TheCinematicPhoenix(final TheCinematicPhoenix card) {
        super(card);
    }

    @Override
    public TheCinematicPhoenix copy() {
        return new TheCinematicPhoenix(this);
    }
}

class TheCinematicPhoenixEffect extends OneShotEffect {

    TheCinematicPhoenixEffect() {
        super(Outcome.Win);
        this.staticText = "If you tapped six legendary creatures this way, you win the game";
    }

    private TheCinematicPhoenixEffect(final TheCinematicPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public TheCinematicPhoenixEffect copy() {
        return new TheCinematicPhoenixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> tapped = (List<Permanent>) getValue("tappedPermanents");
        Player controller = game.getPlayer(source.getControllerId());
        if (tapped == null || tapped.isEmpty() || controller == null) {
            return false;
        }
        if (tapped.stream().allMatch(permanent -> permanent.isLegendary(game))) {
            controller.won(game);
            return true;
        }
        return false;
    }
}
