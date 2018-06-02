
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SpellstutterSprite extends CardImpl {
    
    static final FilterPermanent filter = new FilterPermanent("number of Faeries you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.FAERIE));
    }

    public SpellstutterSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Spellstutter Sprite enters the battlefield, counter target spell with converted mana cost X or less, where X is the number of Faeries you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpellstutterSpriteCounterTargetEffect()));
    }

    public SpellstutterSprite(final SpellstutterSprite card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            int numberFaeries = game.getState().getBattlefield().countAll(filter, ability.getControllerId(), game);
            FilterSpell xFilter = new FilterSpell(new StringBuilder("spell with converted mana cost ").append(numberFaeries).append(" or less").toString());
            xFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, numberFaeries + 1));
            ability.getTargets().clear();
            ability.addTarget(new TargetSpell(xFilter));
        }
    }

    @Override
    public SpellstutterSprite copy() {
        return new SpellstutterSprite(this);
    }
}

class SpellstutterSpriteCounterTargetEffect extends OneShotEffect {

    public SpellstutterSpriteCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public SpellstutterSpriteCounterTargetEffect(final SpellstutterSpriteCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public SpellstutterSpriteCounterTargetEffect copy() {
        return new SpellstutterSpriteCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         * The value of X needs to be determined both when the ability triggers (so you can choose
         * a target) and again when the ability resolves (to check if that target is still legal).
         * If the number of Faeries you control has decreased enough in that time to make the target
         * illegal, Spellstutter Sprite's ability will be countered (and the targeted spell will
         * resolve as normal).
         */
        int numberFaeries = game.getState().getBattlefield().countAll(SpellstutterSprite.filter, source.getControllerId(), game);
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        // If do'nt have any spell targeted
        if (stackObject != null && stackObject.getConvertedManaCost() <= numberFaeries) {
            if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "counter target spell with converted mana cost X or less, where X is the number of Faeries you control";
    }

}
