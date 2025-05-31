package mage.cards.e;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.CastFromGraveyardOnceDuringEachOfYourTurnAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EdgarMasterMachinist extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact spell");

    public EdgarMasterMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Once during each of your turns, you may cast an artifact spell from your graveyard. If you cast a spell this way, that artifact enters tapped.
        Ability ability = new CastFromGraveyardOnceDuringEachOfYourTurnAbility(filter, MageIdentifier.OnceOnYourTurnCastFromGraveyardEntersTapped);
        ability.addEffect(new InfoEffect("If you cast a spell this way, that artifact enters tapped"));
        this.addAbility(ability);

        // Tools -- Whenever Edgar attacks, it gets +X/+0 until end of turn, where X is the greatest mana value among artifacts you control.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS, StaticValue.get(0), Duration.EndOfTurn, "it")
        ).withFlavorWord("Tools").addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS.getHint()));
    }

    private EdgarMasterMachinist(final EdgarMasterMachinist card) {
        super(card);
    }

    @Override
    public EdgarMasterMachinist copy() {
        return new EdgarMasterMachinist(this);
    }
}
