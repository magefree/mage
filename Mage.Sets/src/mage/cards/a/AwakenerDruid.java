

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AwakenerDruid extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "Forest");

    public AwakenerDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Awakener Druid enters the battlefield, target Forest becomes a 4/5 green Treefolk creature for as long as Awakener Druid remains on the battlefield. It's still a land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AwakenerDruidBecomesCreatureEffect(), false);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
    }


    private AwakenerDruid(final AwakenerDruid card) {
        super(card);
    }

    @Override
    public AwakenerDruid copy() {
        return new AwakenerDruid(this);
    }
}

class AwakenerDruidBecomesCreatureEffect extends BecomesCreatureTargetEffect {

    public AwakenerDruidBecomesCreatureEffect() {
        super(new AwakenerDruidToken(), false, true, Duration.WhileOnBattlefield);
        this.staticText = "target Forest becomes a 4/5 green Treefolk creature for as long as {this} remains on the battlefield. It's still a land";
    }

    public AwakenerDruidBecomesCreatureEffect(final AwakenerDruidBecomesCreatureEffect effect) {
        super(effect);
    }

    @Override
    public AwakenerDruidBecomesCreatureEffect copy() {
        return new AwakenerDruidBecomesCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            this.discard();
            return false;
        }
        return super.apply(layer, sublayer, source, game);
    }
}

class AwakenerDruidToken extends TokenImpl {

    public AwakenerDruidToken() {
        super("", "4/5 green Treefolk creature as long as {this} is on the battlefield");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TREEFOLK);
        color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(5);
    }
    public AwakenerDruidToken(final AwakenerDruidToken token) {
        super(token);
    }

    @Override
    public AwakenerDruidToken copy() {
        return new AwakenerDruidToken(this);
    }
}
