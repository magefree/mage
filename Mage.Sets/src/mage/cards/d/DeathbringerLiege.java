

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DeathbringerLiege extends CardImpl {
    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("black creatures");
    private static final FilterSpell filterWhiteSpellCard = new FilterSpell("a white spell");
    private static final FilterSpell filterBlackSpellCard = new FilterSpell("a black spell");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterWhiteSpellCard.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlackSpellCard.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DeathbringerLiege (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/B}{W/B}{W/B}");
        this.subtype.add(SubType.HORROR);


        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterWhite, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBlack, true)));
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filterWhiteSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        ability = new SpellCastControllerTriggeredAbility(new DeathbringerLiegeEffect(), filterBlackSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DeathbringerLiege(final DeathbringerLiege card) {
        super(card);
    }

    @Override
    public DeathbringerLiege copy() {
        return new DeathbringerLiege(this);
    }

}

class DeathbringerLiegeEffect extends OneShotEffect {
    DeathbringerLiegeEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy target creature if it's tapped";
    }

    private DeathbringerLiegeEffect(final DeathbringerLiegeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if (p != null && p.isTapped()) {
            p.destroy(source, game, false);
        }
        return false;
    }

    @Override
    public DeathbringerLiegeEffect copy() {
        return new DeathbringerLiegeEffect(this);
    }

}
