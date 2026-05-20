package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExhibitionTidecaller extends CardImpl {

    public ExhibitionTidecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Opus -- Whenever you cast an instant or sorcery spell, target player mills three cards. If five or more mana was spent to cast that spell, that player mills ten cards instead.
        Ability ability = new OpusAbility(
                new MillCardsTargetEffect(3), new MillCardsTargetEffect(10),
                "target player mills three cards. If five or more mana was spent " +
                        "to cast that spell, that player mills ten cards instead", true
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ExhibitionTidecaller(final ExhibitionTidecaller card) {
        super(card);
    }

    @Override
    public ExhibitionTidecaller copy() {
        return new ExhibitionTidecaller(this);
    }
}
