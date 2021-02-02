
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BowOfNylea extends CardImpl {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public BowOfNylea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.ARTIFACT},"{1}{G}{G}");
        addSuperType(SuperType.LEGENDARY);


        // Attacking creatures you control have deathtouch.
        GainAbilityControlledEffect gainEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, new FilterAttackingCreature("Attacking creatures"), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainEffect));
        
        // {1}{G}, {T}: Choose one - Put a +1/+1 counter on target creature;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        // or Bow of Nylea deals 2 damage to target creature with flying;
        Mode mode = new Mode();
        mode.addEffect(new DamageTargetEffect(2));
        Target target = new TargetCreaturePermanent(filterFlying);
        mode.addTarget(target);
        ability.addMode(mode);
        // or you gain 3 life;
        mode = new Mode();
        mode.addEffect(new GainLifeEffect(3));
        ability.addMode(mode);
        // or put up to four target cards from your graveyard on the bottom of your library in any order.
        mode = new Mode();
        mode.addEffect(new PutCardsFromGraveyardToLibraryEffect());
        mode.addTarget(new TargetCardInYourGraveyard(0,4, new FilterCard()));
        ability.addMode(mode);

        this.addAbility(ability);

    }

    private BowOfNylea(final BowOfNylea card) {
        super(card);
    }

    @Override
    public BowOfNylea copy() {
        return new BowOfNylea(this);
    }
}

class PutCardsFromGraveyardToLibraryEffect extends OneShotEffect {

    public PutCardsFromGraveyardToLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "put up to four target cards from your graveyard on the bottom of your library in any order";
    }

    public PutCardsFromGraveyardToLibraryEffect(final PutCardsFromGraveyardToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public PutCardsFromGraveyardToLibraryEffect copy() {
        return new PutCardsFromGraveyardToLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (UUID cardId : this.getTargetPointer().getTargets(game, source)) {
                Card card = controller.getGraveyard().get(cardId, game);
                if (card != null) {
                    cards.add(card);
                }
            }
            return controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return false;
    }
}
