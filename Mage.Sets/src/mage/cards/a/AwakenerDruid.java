package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

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
        super(
            new CreatureToken(4, 5, "4/5 green Treefolk creature for as long as {this} is on the battlefield", SubType.TREEFOLK)
                .withColor("G"),
            false, true, Duration.WhileOnBattlefield
        );
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            this.discard();
            return false;
        }
        return super.apply(layer, sublayer, source, game);
    }
}
