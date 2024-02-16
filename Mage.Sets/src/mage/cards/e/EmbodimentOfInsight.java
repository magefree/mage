
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class EmbodimentOfInsight extends CardImpl {

    private static final FilterPermanent filterLandCreatures = new FilterPermanent("Land creatures");

    static {
        filterLandCreatures.add(CardType.LAND.getPredicate());
        filterLandCreatures.add(CardType.CREATURE.getPredicate());
    }

    public EmbodimentOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Land creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filterLandCreatures)));

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under you control, you may have target land you control
        // become a 3/3 Elemental creature with haste until end of turn. It's still a land.
        Ability ability = new LandfallAbility(new BecomesCreatureTargetEffect(new EmbodimentOfInsightToken(), false, true, Duration.EndOfTurn), true);
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private EmbodimentOfInsight(final EmbodimentOfInsight card) {
        super(card);
    }

    @Override
    public EmbodimentOfInsight copy() {
        return new EmbodimentOfInsight(this);
    }
}

class EmbodimentOfInsightToken extends TokenImpl {

    public EmbodimentOfInsightToken() {
        super("", "3/3 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(HasteAbility.getInstance());
    }
    private EmbodimentOfInsightToken(final EmbodimentOfInsightToken token) {
        super(token);
    }

    public EmbodimentOfInsightToken copy() {
        return new EmbodimentOfInsightToken(this);
    }
}
