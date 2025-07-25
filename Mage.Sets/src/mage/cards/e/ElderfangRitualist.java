package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ElderfangRitualist extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.ELF, "another target Elf card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ElderfangRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Elderfang Ritualist dies, return another target Elf card from your graveyard to your hand.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ElderfangRitualist(final ElderfangRitualist card) {
        super(card);
    }

    @Override
    public ElderfangRitualist copy() {
        return new ElderfangRitualist(this);
    }
}
