
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author klayhamn
 */
public final class ForiysianTotem extends CardImpl {

    private static final String ruleText = "As long as {this} is a creature, it can block an additional creature each combat.";

    public ForiysianTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {4}{R}: Foriysian Totem becomes a 4/4 red Giant artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ForiysianTotemToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{R}")));

        // As long as Foriysian Totem is a creature, it can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new CanBlockAdditionalCreatureEffect(1), new SourceMatchesFilterCondition(new FilterCreaturePermanent()), ruleText)));
    }

    private ForiysianTotem(final ForiysianTotem card) {
        super(card);
    }

    @Override
    public ForiysianTotem copy() {
        return new ForiysianTotem(this);
    }

}

class ForiysianTotemToken extends TokenImpl {

    public ForiysianTotemToken() {
        super("", "4/4 red Giant artifact creature with trample");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.GIANT);
        color.setRed(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
    }
    public ForiysianTotemToken(final ForiysianTotemToken token) {
        super(token);
    }

    public ForiysianTotemToken copy() {
        return new ForiysianTotemToken(this);
    }
}
