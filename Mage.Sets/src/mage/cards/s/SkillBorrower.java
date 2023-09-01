package mage.cards.s;

import mage.MageInt;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainActivatedAbilitiesOfTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class SkillBorrower extends CardImpl {

    public SkillBorrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card.
        this.addAbility(new SkillBorrowerAbility());
    }

    private SkillBorrower(final SkillBorrower card) {
        super(card);
    }

    @Override
    public SkillBorrower copy() {
        return new SkillBorrower(this);
    }
}

class SkillBorrowerAbility extends StaticAbility {

    private static final FilterCard filter = new FilterCard("an artifact or creature card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ARTIFACT.getPredicate()));
    }

    public SkillBorrowerAbility() {
        super(Zone.BATTLEFIELD, new GainActivatedAbilitiesOfTopCardEffect(filter));
    }

    private SkillBorrowerAbility(final SkillBorrowerAbility ability) {
        super(ability);
    }

    @Override
    public SkillBorrowerAbility copy() {
        return new SkillBorrowerAbility(this);
    }
}

