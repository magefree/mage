
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class AnthousaSetessanHero extends CardImpl {

    public AnthousaSetessanHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Anthousa, Setessan Hero, up to three target lands you control each become 2/2 Warrior creatures until end of turn. They're still lands.
        Ability ability = new HeroicAbility(new BecomesCreatureTargetEffect(new CreatureToken(2, 2, "2/2 Warrior creatures", SubType.WARRIOR),false,true, Duration.EndOfTurn));
        ability.addTarget(new TargetControlledPermanent(0, 3, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, false));
        this.addAbility(ability);
    }

    private AnthousaSetessanHero(final AnthousaSetessanHero card) {
        super(card);
    }

    @Override
    public AnthousaSetessanHero copy() {
        return new AnthousaSetessanHero(this);
    }
}
