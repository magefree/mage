
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandOrOnBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class NazahnReveredBladesmith extends CardImpl {

    private static final FilterControlledCreaturePermanent equippedFilter = new FilterControlledCreaturePermanent("equipped creature you control");

    static {
        equippedFilter.add(EquippedPredicate.instance);
        equippedFilter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final FilterCard filter = new FilterCard("Equipment card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public NazahnReveredBladesmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Nazahn, Revered Bladesmith enters the battlefield, search your library for an Equipment card and reveal it. If you reveal a card named Hammer of Nazahn this way, put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandOrOnBattlefieldEffect(target, true, "Hammer of Nazahn"), false));

        // Whenever an equipped creature you control attacks, you may tap target creature defending player controls.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new NazahnTapEffect(), true, equippedFilter, true);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        ability.setTargetAdjuster(NazahnReveredBladesmithAdjuster.instance);
        this.addAbility(ability);
    }

    private NazahnReveredBladesmith(final NazahnReveredBladesmith card) {
        super(card);
    }

    @Override
    public NazahnReveredBladesmith copy() {
        return new NazahnReveredBladesmith(this);
    }
}

enum NazahnReveredBladesmithAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        FilterCreaturePermanent filterDefender = new FilterCreaturePermanent("creature defending player controls");
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof NazahnTapEffect) {
                filterDefender.add(new ControllerIdPredicate(game.getCombat().getDefendingPlayerId(effect.getTargetPointer().getFirst(game, ability), game)));
                break;
            }
        }
        ability.getTargets().clear();
        TargetCreaturePermanent target = new TargetCreaturePermanent(filterDefender);
        ability.addTarget(target);
    }
}

class NazahnTapEffect extends TapTargetEffect {

    NazahnTapEffect() {
        super();
    }

    NazahnTapEffect(final NazahnTapEffect effect) {
        super(effect);
    }

    @Override
    public NazahnTapEffect copy() {
        return new NazahnTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.tap(source, game);
            return true;
        }
        return false;
    }
}
