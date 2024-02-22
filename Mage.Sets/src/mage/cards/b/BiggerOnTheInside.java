package mage.cards.b;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.TargetPlayer;
import mage.constants.Outcome;

/**
 *
 * @author Skiwkr
 */
public final class BiggerOnTheInside extends CardImpl {

    public BiggerOnTheInside(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant artifact or land
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent has "{T}: Target player adds two mana of any one color. The next spell they cast this turn has cascade."

        Ability ability = new SimpleActivatedAbility(new BiggerOnTheInsideEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new NextSpellCastHasAbilityEffect(new CascadeAbility()));
        this.addAbility(ability);
    }

    private BiggerOnTheInside(final BiggerOnTheInside card) {
        super(card);
    }

    @Override
    public BiggerOnTheInside copy() {
        return new BiggerOnTheInside(this);
    }
}

class BiggerOnTheInsideEffect extends OneShotEffect {

    BiggerOnTheInsideEffect() {
        super(Outcome.Benefit);
        staticText = "Target player adds two mana of any one color. The next spell they cast this turn has cascade.";
    }

    private BiggerOnTheInsideEffect(final BiggerOnTheInsideEffect effect) {
        super(effect);
    }

    @Override
    public BiggerOnTheInsideEffect copy() {
        return new BiggerOnTheInsideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());

        if (controller == null || player == null) {
            return false;
        }

        Mana mana = new Mana();
        ChoiceColor choiceColor = new ChoiceColor();
        choiceColor.setMessage("Choose a color");
        controller.choose(Outcome.Benefit, choiceColor, game);
        choiceColor.increaseMana(mana);//Surely there's a way to do this in one line
        choiceColor.increaseMana(mana);
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}