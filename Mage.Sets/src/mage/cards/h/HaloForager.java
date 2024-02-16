package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaloForager extends CardImpl {

    public HaloForager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Halo Forager enters the battlefield, you may pay {X}. When you do, you may cast target instant or sorcery card with mana value X from a graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HaloForagerPayEffect()));
    }

    private HaloForager(final HaloForager card) {
        super(card);
    }

    @Override
    public HaloForager copy() {
        return new HaloForager(this);
    }
}

class HaloForagerPayEffect extends OneShotEffect {

    HaloForagerPayEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, you may cast target instant or sorcery card " +
                "with mana value X from a graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }

    private HaloForagerPayEffect(final HaloForagerPayEffect effect) {
        super(effect);
    }

    @Override
    public HaloForagerPayEffect copy() {
        return new HaloForagerPayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        if (player == null || !player.chooseUse(outcome, "Pay " + cost.getText() + "?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(costX));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        FilterCard filter = new FilterInstantOrSorceryCard(
                "instant or sorcery card with mana value " + costX + " from a graveyard"
        );
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, costX));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new MayCastTargetThenExileEffect(true), false);
        ability.addTarget(new TargetCardInGraveyard(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
