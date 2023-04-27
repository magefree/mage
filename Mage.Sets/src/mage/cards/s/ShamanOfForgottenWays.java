
package mage.cards.s;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ShamanOfForgottenWays extends CardImpl {

    public ShamanOfForgottenWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(	2);
        this.toughness = new MageInt(3);

        // {T}:Add two mana in any combination of colors. Spend this mana only to cast creature spells.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new ShamanOfForgottenWaysManaBuilder()));
        
        // <i>Formidable</i> &mdash; {9}{G}{G},{T}:Each player's life total becomes the number of creatures they control. Activate the ability only if creatures you control have total power 8 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new ShamanOfForgottenWaysEffect(), 
                new ManaCostsImpl<>("{9}{G}{G}"),
                FormidableCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.setAbilityWord(AbilityWord.FORMIDABLE);        
        this.addAbility(ability);
    }

    private ShamanOfForgottenWays(final ShamanOfForgottenWays card) {
        super(card);
    }

    @Override
    public ShamanOfForgottenWays copy() {
        return new ShamanOfForgottenWays(this);
    }
}

class ShamanOfForgottenWaysManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}

class ShamanOfForgottenWaysEffect extends OneShotEffect {
    
    public ShamanOfForgottenWaysEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player's life total becomes the number of creatures they control";
    }
    
    public ShamanOfForgottenWaysEffect(final ShamanOfForgottenWaysEffect effect) {
        super(effect);
    }
    
    @Override
    public ShamanOfForgottenWaysEffect copy() {
        return new ShamanOfForgottenWaysEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanent filter = new FilterCreaturePermanent();
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null){
                    int numberCreatures = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size();
                    player.setLife(numberCreatures, game, source);
                }
            }
            return true;
        }
        return false;
    }
}
