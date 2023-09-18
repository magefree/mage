package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class DreamStrix extends CardImpl {

    public DreamStrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dream Strix becomes the target of a spell, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(
                new SacrificeSourceEffect().setText("sacrifice it"), StaticFilters.FILTER_SPELL_A
        ));

        // When Dream Strix dies, learn.
        this.addAbility(new DiesSourceTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));
    }

    private DreamStrix(final DreamStrix card) {
        super(card);
    }

    @Override
    public DreamStrix copy() {
        return new DreamStrix(this);
    }
}
