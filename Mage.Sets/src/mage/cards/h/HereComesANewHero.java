package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class HereComesANewHero extends CardImpl {

    public HereComesANewHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{U}");

        // Target player draws X cards. Create a token that's a copy of up to one target creature with mana value X or less.
        this.getSpellAbility().addEffect(
            new DrawCardTargetEffect(GetXValue.instance).setTargetPointer(new FirstTargetPointer())
        );
        this.getSpellAbility().addTarget(new TargetPlayer().setTargetTag(1));
        this.getSpellAbility().addEffect(
            new CreateTokenCopyTargetEffect()
                .setText("create a token that's a copy of up to one target creature with mana value X or less")
                .setTargetPointer(new SecondTargetPointer())
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1).setTargetTag(2));
        this.getSpellAbility().setTargetAdjuster(HereComesANewHeroAdjuster.instance);
    }

    private HereComesANewHero(final HereComesANewHero card) {
        super(card);
    }

    @Override
    public HereComesANewHero copy() {
        return new HereComesANewHero(this);
    }
}

enum HereComesANewHeroAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = CardUtil.getSourceCostsTag(game, ability, "X", 0);

        ability.getTargets().clear();
        ability.addTarget(new TargetPlayer().setTargetTag(1));

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, xValue));
        ability.addTarget(new TargetPermanent(filter).setTargetTag(2));
    }
}
