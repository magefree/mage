package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class WidowMine extends CardImpl {

    public WidowMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // {T}: Name a card. Activate this ability only any time you can cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD,
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL),
                new TapSourceCost()));

        // {R}, {T}, Sacrifice Widow Mine: Widow Mine deals 4 damage to target creature with the last chosen name.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WidowMineEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public WidowMine(final WidowMine card) {
        super(card);
    }

    @Override
    public WidowMine copy() {
        return new WidowMine(this);
    }
}

class WidowMineEffect extends OneShotEffect {

    public WidowMineEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to target creature with the last chosen name";
    }

    public WidowMineEffect(final WidowMineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if(you != null) {
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
            if (cardName == null) {
                return false;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new NamePredicate(cardName));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            if(you.chooseTarget(Outcome.Damage, target, source, game)) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(target.getFirstTarget());
                if(permanent != null) {
                    permanent.damage(4, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WidowMineEffect copy() {
        return new WidowMineEffect(this);
    }
}