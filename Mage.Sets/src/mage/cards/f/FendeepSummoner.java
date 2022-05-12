package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class FendeepSummoner extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent(SubType.SWAMP, "Swamps");

    public FendeepSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}: Up to two target Swamps each become 3/5 Treefolk Warrior creatures in addition to their other types until end of turn.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(new CreatureToken(
                3, 5, "3/5 Treefolk Warrior creatures " +
                "in addition to their other types", SubType.TREEFOLK, SubType.WARRIOR
        ), false, false, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);
    }

    private FendeepSummoner(final FendeepSummoner card) {
        super(card);
    }

    @Override
    public FendeepSummoner copy() {
        return new FendeepSummoner(this);
    }
}
