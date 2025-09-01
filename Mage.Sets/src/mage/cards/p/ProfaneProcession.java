
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ProfaneProcession extends CardImpl {

    public ProfaneProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.t.TombOfTheDuskRose.class;

        // {3}{W}{B}: Exile target creature. Then if there are three or more cards exiled with Profane Procession, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), ProfaneProcessionCondition.instance,
                "Then if there are three or more cards exiled with {this}, transform it"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ProfaneProcession(final ProfaneProcession card) {
        super(card);
    }

    @Override
    public ProfaneProcession copy() {
        return new ProfaneProcession(this);
    }
}

enum ProfaneProcessionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getExile().getExileZone(CardUtil.getExileZoneId(game, source)))
                .filter(cards -> cards.size() >= 3)
                .isPresent();
    }
}
