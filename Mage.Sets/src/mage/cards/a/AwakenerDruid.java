package mage.cards.a;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AwakenerDruid extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FOREST, "Forest");

    public AwakenerDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Awakener Druid enters the battlefield, target Forest becomes a 4/5 green Treefolk creature for as long as Awakener Druid remains on the battlefield. It's still a land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AwakenerDruidBecomesCreatureEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
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

    AwakenerDruidBecomesCreatureEffect() {
        super(new AwakenerDruidToken(), false, true, Duration.WhileOnBattlefield);
        this.staticText = "target Forest becomes a 4/5 green Treefolk creature for as long as {this} remains on the battlefield. It's still a land";
    }

    private AwakenerDruidBecomesCreatureEffect(final AwakenerDruidBecomesCreatureEffect effect) {
        super(effect);
    }

    @Override
    public AwakenerDruidBecomesCreatureEffect copy() {
        return new AwakenerDruidBecomesCreatureEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        if (game.getPermanent(source.getSourceId()) == null) {
            this.discard();
            return false;
        }
        return super.queryAffectedObjects(layer, source, game, affectedObjects);
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

    private AwakenerDruidToken(final AwakenerDruidToken token) {
        super(token);
    }

    @Override
    public AwakenerDruidToken copy() {
        return new AwakenerDruidToken(this);
    }
}
